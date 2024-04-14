import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IWebPage } from 'app/shared/model/web-page.model';
import { getEntities as getWebPages } from 'app/entities/web-page/web-page.reducer';
import { getEntities as getMenus } from 'app/entities/menu/menu.reducer';
import { ITourCategory } from 'app/shared/model/tour-category.model';
import { getEntities as getTourCategories } from 'app/entities/tour-category/tour-category.reducer';
import { IMenu } from 'app/shared/model/menu.model';
import { getEntity, updateEntity, createEntity, reset } from './menu.reducer';

export const MenuUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const webPages = useAppSelector(state => state.webPage.entities);
  const menus = useAppSelector(state => state.menu.entities);
  const tourCategories = useAppSelector(state => state.tourCategory.entities);
  const menuEntity = useAppSelector(state => state.menu.entity);
  const loading = useAppSelector(state => state.menu.loading);
  const updating = useAppSelector(state => state.menu.updating);
  const updateSuccess = useAppSelector(state => state.menu.updateSuccess);

  const handleClose = () => {
    navigate('/menu' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getWebPages({}));
    dispatch(getMenus({}));
    dispatch(getTourCategories({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...menuEntity,
      ...values,
      createdBy: users.find(it => it.id.toString() === values.createdBy?.toString()),
      page: webPages.find(it => it.id.toString() === values.page?.toString()),
      parent: menus.find(it => it.id.toString() === values.parent?.toString()),
      tourCategory: tourCategories.find(it => it.id.toString() === values.tourCategory?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...menuEntity,
          createdBy: menuEntity?.createdBy?.id,
          page: menuEntity?.page?.id,
          parent: menuEntity?.parent?.id,
          tourCategory: menuEntity?.tourCategory?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="xploraAdminApp.menu.home.createOrEditLabel" data-cy="MenuCreateUpdateHeading">
            <Translate contentKey="xploraAdminApp.menu.home.createOrEditLabel">Create or edit a Menu</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="menu-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('xploraAdminApp.menu.code')}
                id="menu-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label={translate('xploraAdminApp.menu.uri')} id="menu-uri" name="uri" data-cy="uri" type="text" />
              <ValidatedField
                label={translate('xploraAdminApp.menu.createdDate')}
                id="menu-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="date"
              />
              <ValidatedField label={translate('xploraAdminApp.menu.icon')} id="menu-icon" name="icon" data-cy="icon" type="text" />
              <ValidatedField
                label={translate('xploraAdminApp.menu.enabled')}
                id="menu-enabled"
                name="enabled"
                data-cy="enabled"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('xploraAdminApp.menu.defaultImage')}
                id="menu-defaultImage"
                name="defaultImage"
                data-cy="defaultImage"
                type="text"
              />
              <ValidatedBlobField
                label={translate('xploraAdminApp.menu.defaultImageData')}
                id="menu-defaultImageData"
                name="defaultImageData"
                data-cy="defaultImageData"
                isImage
                accept="image/*"
              />
              <ValidatedField
                id="menu-createdBy"
                name="createdBy"
                data-cy="createdBy"
                label={translate('xploraAdminApp.menu.createdBy')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="menu-page" name="page" data-cy="page" label={translate('xploraAdminApp.menu.page')} type="select">
                <option value="" key="0" />
                {webPages
                  ? webPages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="menu-parent" name="parent" data-cy="parent" label={translate('xploraAdminApp.menu.parent')} type="select">
                <option value="" key="0" />
                {menus
                  ? menus.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="menu-tourCategory"
                name="tourCategory"
                data-cy="tourCategory"
                label={translate('xploraAdminApp.menu.tourCategory')}
                type="select"
              >
                <option value="" key="0" />
                {tourCategories
                  ? tourCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/menu" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MenuUpdate;
