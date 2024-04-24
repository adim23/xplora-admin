import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './menu.reducer';

export const MenuDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const menuEntity = useAppSelector(state => state.menu.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="menuDetailsHeading">
          <Translate contentKey="xploraAdminApp.menu.detail.title">Menu</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{menuEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.menu.code">Code</Translate>
            </span>
          </dt>
          <dd>{menuEntity.code}</dd>
          <dt>
            <span id="enabled">
              <Translate contentKey="xploraAdminApp.menu.enabled">Enabled</Translate>
            </span>
          </dt>
          <dd>{menuEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="xploraAdminApp.menu.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{menuEntity.icon}</dd>
          <dt>
            <span id="uri">
              <Translate contentKey="xploraAdminApp.menu.uri">Uri</Translate>
            </span>
          </dt>
          <dd>{menuEntity.uri}</dd>
          <dt>
            <span id="defaultImage">
              <Translate contentKey="xploraAdminApp.menu.defaultImage">Default Image</Translate>
            </span>
          </dt>
          <dd>{menuEntity.defaultImage}</dd>
          <dt>
            <span id="defaultImageData">
              <Translate contentKey="xploraAdminApp.menu.defaultImageData">Default Image Data</Translate>
            </span>
          </dt>
          <dd>
            {menuEntity.defaultImageData ? (
              <div>
                {menuEntity.defaultImageDataContentType ? (
                  <a onClick={openFile(menuEntity.defaultImageDataContentType, menuEntity.defaultImageData)}>
                    <img
                      src={`data:${menuEntity.defaultImageDataContentType};base64,${menuEntity.defaultImageData}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {menuEntity.defaultImageDataContentType}, {byteSize(menuEntity.defaultImageData)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.menu.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {menuEntity.createdDate ? <TextFormat value={menuEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.menu.createdBy">Created By</Translate>
          </dt>
          <dd>{menuEntity.createdBy ? menuEntity.createdBy.login : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.menu.page">Page</Translate>
          </dt>
          <dd>{menuEntity.page ? menuEntity.page.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.menu.parent">Parent</Translate>
          </dt>
          <dd>{menuEntity.parent ? menuEntity.parent.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.menu.tourCategory">Tour Category</Translate>
          </dt>
          <dd>{menuEntity.tourCategory ? menuEntity.tourCategory.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.menu.destination">Destination</Translate>
          </dt>
          <dd>{menuEntity.destination ? menuEntity.destination.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/menu" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/menu/${menuEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MenuDetail;
