import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tour-category.reducer';

export const TourCategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tourCategoryEntity = useAppSelector(state => state.tourCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tourCategoryDetailsHeading">
          <Translate contentKey="xploraAdminApp.tourCategory.detail.title">TourCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tourCategoryEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.tourCategory.code">Code</Translate>
            </span>
          </dt>
          <dd>{tourCategoryEntity.code}</dd>
          <dt>
            <span id="enabled">
              <Translate contentKey="xploraAdminApp.tourCategory.enabled">Enabled</Translate>
            </span>
          </dt>
          <dd>{tourCategoryEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="xploraAdminApp.tourCategory.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{tourCategoryEntity.icon}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.tourCategory.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {tourCategoryEntity.createdDate ? (
              <TextFormat value={tourCategoryEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="defaultImage">
              <Translate contentKey="xploraAdminApp.tourCategory.defaultImage">Default Image</Translate>
            </span>
          </dt>
          <dd>{tourCategoryEntity.defaultImage}</dd>
          <dt>
            <span id="defaultImageData">
              <Translate contentKey="xploraAdminApp.tourCategory.defaultImageData">Default Image Data</Translate>
            </span>
          </dt>
          <dd>
            {tourCategoryEntity.defaultImageData ? (
              <div>
                {tourCategoryEntity.defaultImageDataContentType ? (
                  <a onClick={openFile(tourCategoryEntity.defaultImageDataContentType, tourCategoryEntity.defaultImageData)}>
                    <img
                      src={`data:${tourCategoryEntity.defaultImageDataContentType};base64,${tourCategoryEntity.defaultImageData}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {tourCategoryEntity.defaultImageDataContentType}, {byteSize(tourCategoryEntity.defaultImageData)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourCategory.createdBy">Created By</Translate>
          </dt>
          <dd>{tourCategoryEntity.createdBy ? tourCategoryEntity.createdBy.login : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourCategory.parent">Parent</Translate>
          </dt>
          <dd>{tourCategoryEntity.parent ? tourCategoryEntity.parent.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourCategory.tour">Tour</Translate>
          </dt>
          <dd>
            {tourCategoryEntity.tours
              ? tourCategoryEntity.tours.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {tourCategoryEntity.tours && i === tourCategoryEntity.tours.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/tour-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tour-category/${tourCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TourCategoryDetail;
