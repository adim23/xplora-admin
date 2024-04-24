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
import { IDestination } from 'app/shared/model/destination.model';
import { getEntity, updateEntity, createEntity, reset } from './destination.reducer';

export const DestinationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const destinationEntity = useAppSelector(state => state.destination.entity);
  const loading = useAppSelector(state => state.destination.loading);
  const updating = useAppSelector(state => state.destination.updating);
  const updateSuccess = useAppSelector(state => state.destination.updateSuccess);

  const handleClose = () => {
    navigate('/destination' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
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
      ...destinationEntity,
      ...values,
      createdBy: users.find(it => it.id.toString() === values.createdBy?.toString()),
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
          ...destinationEntity,
          createdBy: destinationEntity?.createdBy?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="xploraAdminApp.destination.home.createOrEditLabel" data-cy="DestinationCreateUpdateHeading">
            <Translate contentKey="xploraAdminApp.destination.home.createOrEditLabel">Create or edit a Destination</Translate>
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
                  id="destination-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('xploraAdminApp.destination.code')}
                id="destination-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('xploraAdminApp.destination.enabled')}
                id="destination-enabled"
                name="enabled"
                data-cy="enabled"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('xploraAdminApp.destination.defaultImage')}
                id="destination-defaultImage"
                name="defaultImage"
                data-cy="defaultImage"
                type="text"
              />
              <ValidatedBlobField
                label={translate('xploraAdminApp.destination.defaultImageData')}
                id="destination-defaultImageData"
                name="defaultImageData"
                data-cy="defaultImageData"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('xploraAdminApp.destination.cssStyle')}
                id="destination-cssStyle"
                name="cssStyle"
                data-cy="cssStyle"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.destination.createdDate')}
                id="destination-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="date"
              />
              <ValidatedField
                id="destination-createdBy"
                name="createdBy"
                data-cy="createdBy"
                label={translate('xploraAdminApp.destination.createdBy')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/destination" replace color="info">
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

export default DestinationUpdate;
